package com.romario.usuario.business;

import com.romario.usuario.business.converter.UsuarioConverter;
import com.romario.usuario.business.dto.UsuarioDTO;
import com.romario.usuario.infrastructure.entity.Usuario;
import com.romario.usuario.infrastructure.exceptions.ConflictException;
import com.romario.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.romario.usuario.infrastructure.repository.UsuarioRepository;
import com.romario.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
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

    public Usuario buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("email nao encontrado " + email));
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
}
