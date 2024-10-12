package com.example.AplicacionEjercicioMVC.Service;

import com.example.AplicacionEjercicioMVC.Models.Contrato;
import com.example.AplicacionEjercicioMVC.Repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContratoService {
    @Autowired
    private final ContratoRepository repositorio;

    public ContratoService(ContratoRepository repositorio) {
        this.repositorio = repositorio;
    }

    private List<Contrato> contratos = new ArrayList<>();

    public List<Contrato> findAll(){
        return repositorio.findAll();
    }

    public Contrato registrarContrato(Contrato contrato){
        contrato.setFechaIngreso(new Date());
        repositorio.save(contrato);
        return contrato;
    }

    public Contrato findByCedula(String cedula){
        return repositorio.findByCedula(cedula);
    }

    public Contrato update(String cedula, Contrato contrato){
        Contrato existeContrato = findByCedula(cedula);
        if(existeContrato != null){
            existeContrato.setCliente(contrato.getCliente());
            existeContrato.setCedula(contrato.getCedula());
            existeContrato.setDescripcion(contrato.getDescripcion());
            existeContrato.setValor(contrato.getValor());
            existeContrato.setEstado(contrato.getEstado());
            existeContrato.setFechaFin(contrato.getFechaFin());
            repositorio.save(existeContrato);
        }
        return existeContrato;
    }

    public Boolean delete (String cedula){
        Contrato existeContrato = findByCedula(cedula);
        if (existeContrato != null){
            repositorio.deleteById(existeContrato.getIdContrato());
            return true;
        }else {
            return false;
        }
    }

    public Map<Long, Long> contraContratosPorEmpleado(Date fechaInicio, Date fechaFin){
        List<Contrato> contratos = repositorio.findByFechaIngresoBetween(fechaInicio, fechaFin);
        return contratos.stream()
                .collect(Collectors.groupingBy(contrato -> contrato.getEmpleado().getIdEmpleado(), Collectors.counting()));

                /*
                .collect(Collectors.groupingBy(Contrato::getEmpleado(), Collectors.counting()));
                */
    }

}
