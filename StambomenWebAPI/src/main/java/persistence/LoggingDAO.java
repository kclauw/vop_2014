package persistence;

import domain.Logging;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingDAO implements IDao<Logging>
{

    private Connection con;
    private final Logger logger;
    private final String GETLOGGING = "Select";
    private final String SETLOGGING = "Insert";

    public LoggingDAO()
    {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Logging get(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Logging value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Logging value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Logging value)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Logging> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Logging map(ResultSet res)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
