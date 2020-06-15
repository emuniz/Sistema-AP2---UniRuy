package frb.edu.br.luiseduardo.controladores;

import frb.edu.br.luiseduardo.contratos.IPais;
import frb.edu.br.luiseduardo.entidades.PaisDto;
import frb.edu.br.luiseduardo.repositorios.PaisRepositorio;
import java.util.List;


public class PaisController {
    private PaisDto pais;
    private List<PaisDto> paises = null;
    
    private IPais paisRepositorio = new PaisRepositorio();
    
    
    public PaisController() {
    }

    public PaisDto getPais() {
        return pais;
    }

    public void setPais(PaisDto pais) {
        this.pais = pais;
    }

    public List<PaisDto> getPaises() {
        if(paises == null){
            paises = paisRepositorio.getListaTodos();
        }
        return paises;
    }

    public String prepararInclusao(){
        pais = new PaisDto();
        return "vaiParaIncluir";
    }
    
    public String finalizaInclusao(){
        
        paisRepositorio.incluir(pais);
        paises = null;
        return "voltaParaListagem";
    }
    
    public String finalizaEdicao(){
        paisRepositorio.alterar(pais);
        paises = null;
        return "voltaParaListagem";
    }
    
    public String finalizaDelecao(){
        paisRepositorio.deletar( pais.getPais_id() );
        paises = null;
        return "refresh";
    }
    
}
