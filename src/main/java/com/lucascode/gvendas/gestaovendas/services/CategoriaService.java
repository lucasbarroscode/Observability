package com.lucascode.gvendas.gestaovendas.services;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import com.lucascode.gvendas.gestaovendas.exception.RegraNegocioException;
import com.lucascode.gvendas.gestaovendas.repository.CategoriaRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> listarTodas(){
        return repository.findAll();
    }

    public Optional<Categoria> buscarPorCodigo(Long codigo){
        return repository.findById(codigo);
    }

    public Categoria salvar(Categoria categoria) {

        validarCategoriaDuplicada(categoria);
        return repository.save(categoria);
    }

    public Categoria atualizar(Long codigo, Categoria categoria) {
        Categoria categoriaSalvar = validarCategoriaExiste(codigo);
        validarCategoriaDuplicada(categoria);
        BeanUtils.copyProperties(categoria, categoriaSalvar, "codigo");
        return repository.save(categoriaSalvar);
    }

    public void deletar(Long codigo) {
        repository.deleteById(codigo);
    }

    private Categoria validarCategoriaExiste(Long codigo) {
        Optional<Categoria> categoria = buscarPorCodigo(codigo);
        if(categoria.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return categoria.get();
    }

    private void validarCategoriaDuplicada(Categoria categoria){
        Categoria categoriaEncontrada = repository.findByNome(categoria.getNome());
        if(categoriaEncontrada != null && categoriaEncontrada.getCodigo() != categoria.getCodigo()){
            throw new RegraNegocioException(
                    String.format("A categoria  %s já está cadastrada ", categoria.getNome().toUpperCase()));
        }

    }
}
