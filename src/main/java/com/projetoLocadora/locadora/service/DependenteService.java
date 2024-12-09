package com.projetoLocadora.locadora.service;

import javax.management.relation.RelationTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projetoLocadora.locadora.model.Dependente;
import com.projetoLocadora.locadora.repository.DependenteRepository;
import com.projetoLocadora.locadora.repository.LocacaoRepository;
import com.projetoLocadora.locadora.repository.SocioRepository;

import exception.DependenteEmLocacaoException;
import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "DependenteService", description = "Fornece serviços web REST para acesso e manipulação de dados de dependentes.")
public class DependenteService {

    @Autowired
    private DependenteRepository dependenteRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;
    
    public Dependente saveDependente(Dependente dependenteEntra) {
        return dependenteRepository.save(dependenteEntra);
    }

    public Dependente editDependente(Dependente dependenteEntra) throws RelationTypeNotFoundException {
        Dependente dependente = dependenteRepository.findById(dependenteEntra.getNumInscricao())
                .orElseThrow(() -> new RelationTypeNotFoundException(
                        "Dependente não existe com número de inscrição:" + dependenteEntra.getNumInscricao()));
        dependente.setNumInscricao(dependenteEntra.getNumInscricao());
        dependente.setNome(dependenteEntra.getNome());
        dependente.setDtNascimento(dependenteEntra.getDtNascimento());
        dependente.setSexo(dependenteEntra.getSexo());
        dependente.setEstahAtivo(dependenteEntra.isEstahAtivo());

        return dependenteRepository.save(dependente);
    }

    public Dependente activeDependente(Dependente dependenteEntra) throws RelationTypeNotFoundException {
        Dependente dependente = dependenteRepository.findById(dependenteEntra.getNumInscricao())
                .orElseThrow(() -> new RelationTypeNotFoundException(
                        "Sócio não existe com número de inscrição:" + dependenteEntra.getNumInscricao()));
        dependente.setEstahAtivo(true);

        return dependenteRepository.save(dependente);
    }

    public Dependente desactiveDependente(Dependente dependenteEntra) throws RelationTypeNotFoundException {
        Dependente dependente = dependenteRepository.findById(dependenteEntra.getNumInscricao())
                .orElseThrow(() -> new RelationTypeNotFoundException(
                        "Sócio não existe com número de inscrição:" + dependenteEntra.getNumInscricao()));
        dependente.setEstahAtivo(false);

        return dependenteRepository.save(dependente);
    }

    public Iterable<Dependente> listAll() {
        return dependenteRepository.findAll();
    }
    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    // public List<Dependente> listAllDependentes() {
    // return dependenteRepository.findByDependentesOption();
    // }

    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    // public List<Dependente> listAllDependentesQuery2() {
    // return dependenteRepository.findAllByDependentesAtivos();
    // }

    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    // public List<Dependente> listAllDependentesQuery3() {
    // return dependenteRepository.findAllByDependentesInativos();
    // }

    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    // public List<Dependente> listAllDependentesQuery4() {
    // return dependenteRepository.findAllByDependentesativosesemmulta();
    // }

    public Dependente listIdDependente(Long id) throws RelationTypeNotFoundException {
        return dependenteRepository.findById(id)
                .orElseThrow(
                        () -> new RelationTypeNotFoundException("Dependente não existe com número de inscrição:" + id));
    }

    public void deleteDependente(Long id) throws RelationTypeNotFoundException {
        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new RelationTypeNotFoundException(
                        "Dependente não existe com número de inscrição: " + id));

        boolean isSocio = socioRepository.existsByNome(dependente.getNome());
        System.out.println("Dependente é sócio? " + isSocio);

        if (isSocio) {
            throw new IllegalArgumentException("O dependente é um sócio e não pode ser excluído");
        } else {
            dependenteRepository.delete(dependente);
        }
    }

}