package com.projetoLocadora.locadora.service;

import java.util.List;
import java.util.UUID;

import javax.management.relation.RelationTypeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.projetoLocadora.locadora.model.Dependente;
import com.projetoLocadora.locadora.model.Locacao;
import com.projetoLocadora.locadora.model.Socio;
import com.projetoLocadora.locadora.repository.LocacaoRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "LocacaoService", description = "Fornece serviços web REST para acesso e manipulação de dados de Locacao.")
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepo;

    public Locacao saveAll(Locacao locacao) {
        return locacaoRepo.save(locacao);
    }

    public Locacao editAll(Locacao locacao) throws RelationTypeNotFoundException {
        Locacao editado = locacaoRepo.findById(locacao.getIdLocacao())
                .orElseThrow(
                        () -> new RelationTypeNotFoundException(
                                "Locação não existe com id :" + locacao.getIdLocacao()));

        editado.setDtLocacao(locacao.getDtLocacao());
        editado.setDtDevolucaoEfetiva(locacao.getDtDevolucaoEfetiva());
        editado.setDtDevolucaoPrevista(locacao.getDtDevolucaoEfetiva());
        editado.setMultaCobrada(locacao.getMultaCobrada());

        return locacaoRepo.save(editado);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Locacao> listAllNaoDevolvido() {
        return locacaoRepo.findAllLocacaoNaoDevolvida();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Locacao> listAllDevolvido() {
        return locacaoRepo.findAllLocacaoDevolvida();
    }

    public Locacao listId(UUID idLocacao) throws RelationTypeNotFoundException {
        return locacaoRepo.findById(idLocacao)
                .orElseThrow(() -> new RelationTypeNotFoundException("Locação não existe com id :" + idLocacao));
    }

    public void deleteId(UUID idLocacao) throws RelationTypeNotFoundException {
        Locacao locacao = locacaoRepo.findById(idLocacao)
                .orElseThrow(() -> new RelationTypeNotFoundException("Locação não existe com id :" + idLocacao));
        locacaoRepo.delete(locacao);
    }
}