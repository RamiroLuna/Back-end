/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.petstar.configurations.PoolDataSource;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;
import org.petstar.dto.UserSonarhDTO;

/**
 *
 * @author Tech-Pro
 */

public class UsersDAO {

    /**
     * Consulta Usuarios Sonarh
     * Metodo que devuelve lista de todos los usuuarios Sonarh
     * @return
     * @throws Exception 
     */
    public List<UserSonarhDTO> getUsersSonarh() throws Exception {
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append(" EXEC sp_SelectSonarh ");
        
        ResultSetHandler rsh = new BeanListHandler(UserSonarhDTO.class);
        List<UserSonarhDTO> usuarios_sonarh = (List<UserSonarhDTO>) qr.query(sql.toString(), rsh); 
        return usuarios_sonarh;
    }
       
    /**
     * Perfil Sonarh
     * Metodo que devuelve la información a detalle de un usuario sonarh
     * @param numeroEmpleado
     * @return
     * @throws Exception 
     */
    public UserSonarhDTO getUserSonarhById(int numeroEmpleado) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_selectSonarhById ?");
        Object[] params = {
            numeroEmpleado
        };
        
        ResultSetHandler rsh = new BeanHandler(UserSonarhDTO.class);
        UserSonarhDTO datosUsuario = (UserSonarhDTO) qr.query(sql.toString(), rsh, params);
        return datosUsuario;
    }
    
    /**
     * Validación Password
     * Metodo que realiza la validacion del password del usuario
     * @param contraseniaAnterior
     * @param idUsuario
     * @return
     * @throws Exception 
     */
    public ResultInteger validaPassword(String contraseniaAnterior, int idUsuario) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_validaPetUsuarioByPass ?, ?");
        Object[] params = {
            idUsuario, contraseniaAnterior 
        };
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger resultInteger = (ResultInteger)  qr.query(sql.toString(), rsh, params);

        return resultInteger;
        
    }
    
    /**
     * Actualización de Password
     * Metodo que actualiza el password del usuario
     * @param contraseniaNueva
     * @param idUsuario
     * @throws Exception 
     */
    public void changePassword(String contraseniaNueva, int idUsuario) throws Exception{
        
       DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_UpdatePassPetUsuarios ?, ?");
        Object[] params = {
            idUsuario, contraseniaNueva 
        };
        
        qr.update(sql.toString(), params);
       
    }

    /**
     * Actualización de Usuario
     * Metodo que actiualiza los datos de un usuario.
     * @param idUsuario
     * @param idTurno
     * @param idPerfil
     * @param activo
     * @throws Exception 
     */
    public void updatePerfilUser(int idUsuario, int idTurno, int idPerfil, int activo) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_updatePetUsuarios ?, ?, ?, ?");
        Object[] params = {
            idUsuario, idTurno, idPerfil, activo
        };
        
        qr.update(sql.toString(), params);
    }
    
    /**
     * Registro de un Usuario
     * Metodo que permite registrar usuarios nuevos al sistema.
     * @param numeroEmpleado
     * @param idLinea
     * @param idGrupo
     * @param fecha
     * @param idSistema
     * @param idUserRegistra
     * @throws Exception 
     */
    public void insertNewUser(int numeroEmpleado, int idLinea, int idGrupo, Date fecha,
            int idSistema, int idUserRegistra) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_insertUsuarios ?, ?, ?, ?, ?, ?");
        Object[] params = {
            numeroEmpleado, idSistema, idGrupo, idLinea, fecha, idUserRegistra
        };
        
        qr.update(sql.toString(), params);
    }
    
    /**
     * Consulta de Usuarios
     * Metodo que devuelve la lista de todos los usuarios validos del sistema.
     * @return
     * @throws Exception 
     */
    public List<UserDTO> getUsersETAD() throws Exception {
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_selectUsuariosEtad");
        ResultSetHandler rsh = new BeanListHandler(UserDTO.class);
        List<UserDTO> usuariosETAD = (List<UserDTO>) qr.query(sql.toString(), rsh); 
        return usuariosETAD;
    }
    
    public UserDTO getUserEtadByID(int idAcceso) throws SQLException{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectEtadById ?");
        Object[] params = { idAcceso };
        
        ResultSetHandler rsh = new BeanHandler(UserDTO.class);
        UserDTO usuarioETAD = (UserDTO) qr.query(sql.toString(), rsh, params);
        return usuarioETAD;
    }
    
    /**
     * Eliminación de USuarios
     * Metodo para eliminar usuarios del sistema
     * @param idUsers
     * @throws Exception 
     */
    public void deleteUsersETAD(int idUsers) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_deletePetUsuarios ?");
        Object[] params = {
            idUsers
        };
        
        qr.update(sql.toString(), params);
    }
    
    /**
     * Validación usuario valido
     * Metodo que valida que el usuario Sonarh no este registrado en ETAD
     * @param numeroEmpleado
     * @return
     * @throws Exception 
     */
    public ResultInteger validaExistUsers(int numeroEmpleado) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT COUNT(1) AS result FROM ETADSonarh WHERE NumEmpleado = ?");
        Object[] params = {
            numeroEmpleado
        };
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger result = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return result;
    }
    
    /**
     * Validación Usuario ETAD
     * Metodo que valida que el usuario ETAD exista
     * @param idAcceso
     * @return
     * @throws Exception 
     */
    public ResultInteger validaExistUsersETAD(int idAcceso) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT COUNT(1) AS result FROM pet_acceso WHERE id_acceso = ?");
        Object[] params = { idAcceso };
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger result = (ResultInteger) qr.query(sql.toString(), rsh, params);
        return result;
    }
    
    public ResultInteger getIdUserByNumeroEmpleado(int numeroEmpleado) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT id_acceso AS result FROM pet_acceso WHERE usuario_sonarh = ")
                .append(numeroEmpleado);
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger result = (ResultInteger) qr.query(sql.toString(), rsh);
        return result;
    }
    
    public void registraPerfilByUser(int idAcceso, int idPerfil) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
      
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_insert_petPerfilAcceso ?, ?");
        Object[] params = {
            idAcceso, idPerfil
        };
        
        qr.update(sql.toString(), params);
    }
}
