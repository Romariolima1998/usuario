package com.romario.usuario.business;

import com.romario.usuario.infrastructure.client.ViaCepClient;
import com.romario.usuario.infrastructure.client.ViaCepDTO;
import com.romario.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscaDadosEndereco(String cep){
        return viaCepClient.buscaDadosEndereco(limpaCep(cep));
    }

    private String limpaCep(String cep){
        String cepFormatado = cep.replace(" ", "").replace("-", "");

        if(!cepFormatado.matches("[0-9]+") || !Objects.equals(cepFormatado.length(), 8)){
            throw new IllegalArgumentException("cep contem caracteres invalido");
        }

        return cepFormatado;
    }
}
