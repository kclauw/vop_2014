/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel
{

    private List<UserDTO> list;
    private String[] columnNames =
    {
        "User", "Language"
    };

    public UserTableModel(List<UserDTO> l)
    {
        list = l;
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    public int getRowCount()
    {
        return list.size();

    }

    public int getColumnCount()
    {
        return 2;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {

        if (columnIndex == 0)
        {
            return list.get(rowIndex).getUsername();
        }
        if (columnIndex == 1)
        {
            return list.get(rowIndex).getLanguage();
        }
        if (columnIndex == 2)
        {
            return list.get(rowIndex).getRole();
        }

        if (columnIndex == 3)
        {
            return list.get(rowIndex);
        }

        return null;
    }

}
