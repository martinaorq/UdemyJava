
package datos;

import domain.PersonaDTO;
import java.sql.SQLException;
import java.util.List;

public interface PersonaDAO {
    List<PersonaDTO> select() throws SQLException;
    int insert(PersonaDTO persona) throws SQLException;
    int update(PersonaDTO persona, int idPersona) throws SQLException;
    int delete(int idPersona) throws SQLException;
}
