package com.projetoLocadora.locadora.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.RelationTypeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.projetoLocadora.locadora.model.Dependente;
import com.projetoLocadora.locadora.model.Item;
import com.projetoLocadora.locadora.model.Socio;
import com.projetoLocadora.locadora.repository.DependenteRepository;
import com.projetoLocadora.locadora.repository.SocioRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "SocioService", description = "Fornece serviços web REST para acesso e manipulação de dados de sócios.")
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private DependenteRepository dependenteRepository;

    public Socio saveSocio(Socio socioEntra) {
        if (socioEntra.getDependentes().size() > 0) {
            long countActiveDependents = socioEntra.getDependentes()
                    .stream()
                    .filter(Dependente::isEstahAtivo)
                    .count();

            if (countActiveDependents <= 3) {
                return socioRepository.save(socioEntra);
            } else {
                throw new IllegalArgumentException(
                        "Não é possível salvar o sócio, pois existem mais de 3 dependentes ativos.");
            }
        } else {
            return socioRepository.save(socioEntra);
        }
    }

    public Iterable<Socio> listAll() {
        return socioRepository.findAll();
    }

    public Socio activeSocio(Socio socioEntra) throws RelationTypeNotFoundException {
        Socio socio = socioRepository.findByNome(socioEntra.getNome())
                .orElseThrow(() -> new RelationTypeNotFoundException(
                        "Sócio não existe com número de inscrição:" + socioEntra.getNome()));
        socio.setEstahAtivo(true);
        if (!socio.getDependentes().isEmpty()) {
            List<Dependente> dList = new ArrayList<>();
            if (socio.getDependentes().size() > 0 && socio.getDependentes().size() <= 3) {
                for (Dependente element : socio.getDependentes()) {
                    element.setEstahAtivo(true);
                    dList.add(element);
                }
                socio.setDependentes(dList);
            } else if (socio.getDependentes().size() > 3) {
                for (int i = 0; i < 3; i++) {
                    Dependente element = socio.getDependentes().get(i);
                    element.setEstahAtivo(true);
                    dList.add(element);
                }
                for (int i = 3; i < socio.getDependentes().size(); i++) {
                    Dependente element = socio.getDependentes().get(i);
                    element.setEstahAtivo(false);
                    dList.add(element);
                }
                socio.setDependentes(dList);
            }
        }

        List<Dependente> dependentes = dependenteRepository.findAll();
        for (Dependente dependente : dependentes) {
            if (socio.getNome().equals(dependente.getNome())) {
                dependente.setEstahAtivo(true);
                socioRepository.save(socio);
                break;
            }
        }
        return socioRepository.save(socio);
    }

    public Socio desactiveSocio(Socio socioEntra) throws RelationTypeNotFoundException {
        Socio socio = socioRepository.findById(socioEntra.getNumInscricao())
                .orElseThrow(() -> new RelationTypeNotFoundException(
                        "Sócio não existe com número de inscrição:" + socioEntra.getNumInscricao()));
        socio.setEstahAtivo(false);
        if (socio.getDependentes() != null && !socio.getDependentes().isEmpty()) {
            for (Dependente dependente : socio.getDependentes()) {
                dependente.setEstahAtivo(false);
                dependenteRepository.save(dependente);
            }
        }

        List<Dependente> dependentes = dependenteRepository.findAll();
        for (Dependente dependente : dependentes) {
            if (socio.getNome().equals(dependente.getNome())) {
                dependente.setEstahAtivo(false);
                socioRepository.save(socio);
                break;
            }
        }

        return socioRepository.save(socio);
    }

    public Socio editSocio(Socio socioEntra) throws RelationTypeNotFoundException {
        Socio socio = socioRepository.findById(socioEntra.getNumInscricao())
                .orElseThrow(() -> new RelationTypeNotFoundException(
                        "Sócio não encontrado com o nome: " + socioEntra.getNumInscricao()));

        socio.setNumInscricao(socioEntra.getNumInscricao());
        socio.setNome(socioEntra.getNome());
        socio.setDtNascimento(socioEntra.getDtNascimento());
        socio.setSexo(socioEntra.getSexo());
        socio.setEstahAtivo(socioEntra.isEstahAtivo());
        socio.setCpf(socioEntra.getCpf());
        socio.setEndereco(socioEntra.getEndereco());
        socio.setTel(socioEntra.getTel());
        socio.setDependentes(socioEntra.getDependentes());
        return socio;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Socio> listAllSociosAtivos() {
        return socioRepository.findAllBySocioAtivo();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Socio> listAllSociosInativos() {
        return socioRepository.findAllBySocioInativo();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Socio> listAllSociosAtivoseSemMulta() {
        return socioRepository.findAllBySocioAtivoeSemMulta();
    }

    public Socio listIdSocio(Long id) throws RelationTypeNotFoundException {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RelationTypeNotFoundException("Sócio não existe com número de inscrição:" + id));
    }

    public void deleteSocio(Long id) throws RelationTypeNotFoundException {
        Socio deletado = socioRepository.findById(id)
                .orElseThrow(() -> new RelationTypeNotFoundException("Sócio não existe com número de inscrição:" + id));

        socioRepository.delete(deletado);
    }
}