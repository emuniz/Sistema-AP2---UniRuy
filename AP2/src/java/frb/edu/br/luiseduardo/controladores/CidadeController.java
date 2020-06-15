package frb.edu.br.luiseduardo.controladores;


import frb.edu.br.luiseduardo.contratos.ICidade;
import frb.edu.br.luiseduardo.entidades.CidadeDto;
import frb.edu.br.luiseduardo.repositorios.CidadeRepositorio;

import java.util.List;

public class CidadeController {
    private CidadeDto cidade;
    private List<CidadeDto> cidades = null;
    
    private ICidade cidadeRepositorio = new CidadeRepositorio();
    
    public CidadeController() {
    }

    public CidadeDto getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDto cidade) {
        this.cidade = cidade;
    }

    public List<CidadeDto> getCidades() {
        if(cidades == null){
            cidades = cidadeRepositorio.getListaTodos();
        }
        return cidades;
    }
    public String prepararInclusao(){
        cidade = new CidadeDto();
        return "vaiParaIncluir";
    }
    
    public String finalizaInclusao(){
        
        cidadeRepositorio.incluir(cidade);
        cidades = null;
        return "voltaParaListagem";
    }
    
    public String finalizaEdicao(){
        cidadeRepositorio.alterar(cidade);
        cidades = null;
        return "voltaParaListagem";
    }
    
    public String finalizaDelecao(){
        cidadeRepositorio.deletar( cidade.getCidade_id());
        cidades = null;
        return "refresh";
    }
    
    
}
