package com.romario.usuario.business;

import com.romario.usuario.business.converter.UsuarioConverter;
import com.romario.usuario.business.dto.EnderecoDTO;
import com.romario.usuario.business.dto.TelefoneDTO;
import com.romario.usuario.business.dto.UsuarioDTO;
import com.romario.usuario.infrastructure.entity.Endereco;
import com.romario.usuario.infrastructure.entity.Telefone;
import com.romario.usuario.infrastructure.entity.Usuario;
import com.romario.usuario.infrastructure.exceptions.ConflictException;
import com.romario.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.romario.usuario.infrastructure.repository.EnderecoRepository;
import com.romario.usuario.infrastructure.repository.TelefoneRepository;
import com.romario.usuario.infrastructure.repository.UsuarioRepository;
import com.romario.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("email ja cadastrado");
            }
        } catch (ConflictException e) {
            throw new ConflictException("email ja cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("email nao encontrado " + email));

        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        String email = jwtUtil.extractUsernameToken(token.substring(7));

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("email nao localizado") );
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        if(dto.getSenha() != null){
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public EnderecoDTO atualizaEndereco(Long id, EnderecoDTO dto){
        Endereco entity =  enderecoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("id nao localizado " + id));
        Endereco endereco = usuarioConverter.updateEndereco(dto, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long id, TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("id nao localizado " + id)
        );
        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
