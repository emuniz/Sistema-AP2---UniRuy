package frb.edu.br.luiseduardo.repositorios;

import frb.edu.br.luiseduardo.contratos.ICidade;
import frb.edu.br.luiseduardo.data.DaoUtil;
import frb.edu.br.luiseduardo.entidades.CidadeDto;
import frb.edu.br.luiseduardo.entidades.PaisDto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CidadeRepositorio extends DaoUtil implements ICidade {
    public CidadeRepositorio() {
        super();
    } 

    @Override
    public boolean incluir(CidadeDto cidade) {
        
        String sql = "INSERT INTO cidade (cidade, pais_id) VALUES (?, ?) ";
        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setString(1, cidade.getCidade());
            ps.setInt(2, cidade.getPais_id().getPais_id());
                       //Se der erro é aqui em cima
                       
            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }

    @Override
    public boolean alterar(CidadeDto cidade) {
        String sql = "UPDATE cidade SET cidade=?, pais_id=? WHERE cidade_id=?";
        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setString(1, cidade.getCidade());
            ps.setInt(2, cidade.getPais_id().getPais_id());
             ps.setInt(3, cidade.getCidade_id());
            
            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }

    @Override
    public boolean deletar(int id) {
        String sql = "DELETE FROM cidade " +
                     " WHERE cidade_id=?";
        PreparedStatement ps;
        int ret=-1;
        try {
            ps = getPreparedStatement(sql);
            ps.setInt(1, id);
            ret = ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException ex){
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret >0;
    }

    @Override
    public CidadeDto getRegistroPorId(int id) {
         CidadeDto cidade = new CidadeDto();

        String sql = "SELECT cidade_id, cidade, pais_id, ultima_atualizacao FROM cidade WHERE cidade_id=?";

        PaisRepositorio pais = new PaisRepositorio();
        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while ( rs.next() ) {
                cidade = new CidadeDto(rs.getInt("cidade_id"), 
                        rs.getString("cidade"), 
                        pais.getRegistroPorId(rs.getInt("pais_id")),
                        rs.getTimestamp("ultima_atualizacao"));
            }
            rs.close();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException ex) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cidade;
    }

    @Override
    public List<CidadeDto> getListaTodos() {
        List<CidadeDto> cidade = new LinkedList<CidadeDto>();
        
        String sql = "SELECT cidade_id, cidade, pais_id, ultima_atualizacao FROM cidade";
        
        PaisRepositorio paises = new PaisRepositorio();
        
        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while ( rs.next() ) {
                
                PaisDto pais = paises.getRegistroPorId(rs.getInt("pais_id"));
                cidade.add( new CidadeDto( rs.getInt("cidade_id"), 
                        rs.getString("cidade"), 
                        pais,
                        rs.getTimestamp("ultima_atualizacao")
                        )
                );
            }
            rs.close();
            ps.close();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException ex) {
            Logger.getLogger(CidadeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }     
        
        return cidade;
    }
}
