/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import tres.domain.Board;
import java.util.List;

/**
*
* @author Gwiza
*/
public interface IBoard {
     public Board saveUsers(Board board);
        public List<Board> getListUsers();
         public Board gettUserById(int userId,String primaryKeyclomunName);
         public Board UpdateUsers(Board users);
public Board getUsersWithQuery(final String[] propertyName, final Object[] value, final String hqlStatement);
}
