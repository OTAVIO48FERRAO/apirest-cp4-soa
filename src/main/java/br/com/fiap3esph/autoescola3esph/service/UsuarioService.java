package br.com.fiap3esph.autoescola3esph.service;

import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.domain.usuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DadosListagemUsuario cadastrarUsuario(DadosCadastroUsuario dados) {
        if (repository.existsByLogin(dados.login())) {
            throw new ValidacaoException("Já existe um usuário cadastrado com este login!");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        Usuario usuario = new Usuario(null, dados.login(), senhaCriptografada, dados.perfil());
        Usuario saved = repository.save(usuario);
        return new DadosListagemUsuario(saved);
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemUsuario> listarUsuarios(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(DadosListagemUsuario::new);
    }

    @Transactional
    public DadosListagemUsuario atualizarPerfil(DadosAtualizacaoPerfilUsuario dados) {
        Usuario usuario = repository
                .findById(dados.id())
                .orElseThrow(() ->
                        new UsuarioNotFoundException("ID do usuário informado não existe!"));
        usuario.atualizarPerfil(dados.perfil());
        Usuario saved = repository.save(usuario);
        return new DadosListagemUsuario(saved);
    }

    @Transactional
    public void excluirUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNotFoundException("ID do usuário informado não existe!");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void alterarPropriaSenha(Usuario usuarioAutenticado, DadosAlteracaoSenha dados) {
        if (!passwordEncoder.matches(dados.senhaAtual(), usuarioAutenticado.getPassword())) {
            throw new SenhaInvalidaException("Senha atual informada está incorreta!");
        }

        String novaSenhaCriptografada = passwordEncoder.encode(dados.novaSenha());
        usuarioAutenticado.alterarSenha(novaSenhaCriptografada);
        repository.save(usuarioAutenticado);
    }
}
