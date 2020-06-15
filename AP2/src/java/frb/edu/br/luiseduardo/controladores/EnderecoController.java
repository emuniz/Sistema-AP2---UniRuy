package frb.edu.br.luiseduardo.controladores;

import frb.edu.br.luiseduardo.contratos.IEndereco;
import frb.edu.br.luiseduardo.entidades.EnderecoDto;
import frb.edu.br.luiseduardo.repositorios.EnderecoRepositorio;
import java.util.List;

public class EnderecoController {
    private EnderecoDto endereco;
    private List<EnderecoDto> enderecos = null;
    
    private IEndereco enderecoRepositorio = new EnderecoRepositorio();  

    public EnderecoController() {
    }

    public EnderecoDto getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDto endereco) {
        this.endereco = endereco;
    }

    public List<EnderecoDto> getEnderecos() {
        if(enderecos == null){
            enderecos = enderecoRepositorio.getListaTodos();
        }
        return enderecos;
    }
    
    public String prepararInclusao(){
        endereco = new EnderecoDto();
        return "vaiParaIncluir";
    }
    
    public String finalizaInclusao(){
        enderecoRepositorio.incluir(endereco);
        enderecos = null;
        return "voltaParaListagem";
    }
    
    public String finalizaEdicao(){
        enderecoRepositorio.alterar(endereco);
        enderecos = null;
        return "voltaParaListagem";
    }
    
    public String finalizaDelecao(){
        enderecoRepositorio.deletar(endereco.getEndereco_id());
        enderecos = null;
        return "refresh";
    }
    

    

}

