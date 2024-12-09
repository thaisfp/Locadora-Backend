package com.projetoLocadora.locadora.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.relation.RelationTypeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.projetoLocadora.locadora.model.Cliente;
import com.projetoLocadora.locadora.model.Dependente;
import com.projetoLocadora.locadora.model.Item;
import com.projetoLocadora.locadora.model.Locacao;
import com.projetoLocadora.locadora.model.Socio;
import com.projetoLocadora.locadora.repository.ClienteRepository;
import com.projetoLocadora.locadora.repository.LocacaoRepository;

import exception.ClienteEmDebitoException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@Service
@Tag(name = "LocacaoService", description = "Fornece serviços web REST para acesso e manipulação de dados de Locacao.")
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepo;

    @Autowired
    private ClienteRepository clienteRepository;

    public Locacao saveAll(Locacao locacao) {
        System.out.println("Cliente ID enviado: " + locacao.getCliente().getNumInscricao());

        Cliente clienteExistente = clienteRepository.findById(locacao.getCliente().getNumInscricao())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        verificarDebitoCliente(clienteExistente);
        verificarItemDisponivel(locacao.getItem());

        locacao.setCliente(clienteExistente);
        return locacaoRepo.save(locacao);
    }

    public Locacao editAll(Locacao locacao) throws RelationTypeNotFoundException {
        Locacao editado = locacaoRepo.findById(locacao.getIdLocacao())
                .orElseThrow(
                        () -> new RelationTypeNotFoundException(
                                "Locação não existe com id :" + locacao.getIdLocacao()));

        editado.setDtLocacao(locacao.getDtLocacao());
        editado.setDtDevolucaoEfetiva(locacao.getDtDevolucaoEfetiva());
        editado.setDtDevolucaoPrevista(locacao.getDtDevolucaoPrevista());
        editado.setMultaCobrada(locacao.getMultaCobrada());
        editado.setValorCobrado(locacao.getValorCobrado());
        editado.setCliente(locacao.getCliente());
        editado.setItem(locacao.getItem());
        
        return locacaoRepo.save(editado);

    }

    public Iterable<Locacao> listAll() {
        return locacaoRepo.findAll();
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

    public void verificarDebitoCliente(Cliente cliente) {
        List<Locacao> locacoesEmAtraso = locacaoRepo.findByCliente(cliente)
                .stream()
                .filter(locacao -> locacao.getDtDevolucaoEfetiva() == null)
                .collect(Collectors.toList());

        if (!locacoesEmAtraso.isEmpty() ) {
            throw new ClienteEmDebitoException("O cliente possui locações em aberto.");
        }
    }

    public void verificarItemDisponivel(Item item) {
        List<Locacao> locacoesEmAtraso = locacaoRepo.findByItem(item)
                .stream()
                .filter(locacao -> locacao.getDtDevolucaoEfetiva() == null)
                .collect(Collectors.toList());

        if (!locacoesEmAtraso.isEmpty()) {
            Date dataPrevista = locacoesEmAtraso.get(0).getDtDevolucaoPrevista();
            Instant instant = dataPrevista.toInstant();

            LocalDate localDate = instant.atZone(ZoneId.of("UTC")).toLocalDate();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = localDate.format(formatter);

            throw new ClienteEmDebitoException("O item está em locação e estará disponível a partir do dia: " + dataFormatada);
        }
    }

}