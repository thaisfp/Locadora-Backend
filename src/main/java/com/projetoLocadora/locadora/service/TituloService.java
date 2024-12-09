package com.projetoLocadora.locadora.service;
import java.util.List;
import java.util.UUID;
import javax.management.relation.RelationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projetoLocadora.locadora.model.Titulo;
import com.projetoLocadora.locadora.repository.TituloRepository;

@Service
public class TituloService {

    @Autowired
    private TituloRepository repository;

    public Titulo saveAll(Titulo titulo) {
        repository.save(titulo);
        return titulo;
    }

    public Iterable<Titulo> listAll(){
        return repository.findAll();
    }

    public Titulo listId (UUID id) throws  RelationNotFoundException{
        return repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe titulo com este id " + id));
    }

    public void deletarId(UUID id) throws RelationNotFoundException{
        Titulo deletado  = repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe titulo com este id " + id));
        repository.delete(deletado);
    }

    public Titulo editId (Titulo titulo, UUID id) throws RelationNotFoundException{
        Titulo alterado = repository.findById(id).orElseThrow(() -> new RelationNotFoundException("Não existe titulo com este id " + id));
        
        alterado.setAno(titulo.getAno());
        alterado.setAtores(titulo.getAtores());
        alterado.setNome(titulo.getNome());
        alterado.setSinopse(titulo.getSinopse());
        alterado.setCategoria(titulo.getCategoria());
        alterado.setClasse(titulo.getClasse());
        alterado.setDiretor(titulo.getDiretor());

        return repository.save(alterado);
    }

    public List<Titulo> listTituloNome(String nome) {
        return repository.findByNome(nome);
    }

    public List<Titulo> listTituloAtor(String nomeator) {
        return repository.findByAtores(nomeator);
    }

    public List<Titulo> listTituloCategoria(String nomeCategoria) {
        System.out.println("VEIO AQUI O PARAMETRO EM SERVICE:" + nomeCategoria);
        System.out.println("VEIO NA QUERY:" + repository.findByCategoria(nomeCategoria));
        return repository.findByCategoria(nomeCategoria);
    }
}
