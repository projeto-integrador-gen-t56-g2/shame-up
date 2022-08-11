package br.org.shameupinc.shameup.service;

import br.org.shameupinc.shameup.model.Usuario;
import br.org.shameupinc.shameup.model.UsuarioLogin;
import br.org.shameupinc.shameup.repository.UsuarioRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario){
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email de usuário já existe!", null);

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return Optional.of(usuarioRepository.save(usuario));
    }

    public Optional<Usuario> atualizarUsuario(Usuario usuario) {
        if (usuarioRepository.findById(usuario.getId()).isPresent()) {
            Optional<Usuario> buscaUsuario = usuarioRepository.
                    findByEmail(usuario.getEmail());
            if (buscaUsuario.isPresent()) {
                if (buscaUsuario.get().getId() != usuario.getId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Email de usuário já existe!", null);
            }
            usuario.setSenha(criptografarSenha(usuario.getSenha()));
            return Optional.of(usuarioRepository.save(usuario));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Email de usuário não encontrado!", null);
    }

    public Optional<UsuarioLogin> logarUsuario(
            Optional<UsuarioLogin> usuarioLogin) {
        Optional<Usuario> usuario = usuarioRepository
                .findByEmail(usuarioLogin.get().getEmail());
        if (usuario.isPresent()) {
            if (compararSenhas(usuarioLogin.get().getSenha(),
                    usuario.get().getSenha())) {
                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setCnpj(usuario.get().getCnpj());
                usuarioLogin.get().setCpf(usuario.get().getCpf());
                usuarioLogin.get().setData_nascimento(usuario.get().getData_nascimento());
                usuarioLogin.get().setTipo(usuario.get().getTipo());
                usuarioLogin.get().setEmail(usuario.get().getEmail());
                usuarioLogin.get().setToken(
                        gerarBasicToken(usuarioLogin.get().getEmail(),
                                usuarioLogin.get().getSenha()));
                usuarioLogin.get().setSenha(usuario.get().getSenha());
                return usuarioLogin;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email do usuario ou senha inválidos!", null);
    }

    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaEncoder = encoder.encode(senha);
        return senhaEncoder;
    }

    private boolean compararSenhas(String senhaDigitada,
                                   String senhaBanco) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(senhaDigitada, senhaBanco);
    }

    private String gerarBasicToken(String email, String password) {
        String estrutura = email + ":" + password;
        byte[] estruturaBase64 = Base64.encodeBase64(
                estrutura.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(estruturaBase64);
    }
}
