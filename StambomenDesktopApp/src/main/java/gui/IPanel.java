package gui;

import dto.ImageTypeDTO;
import dto.ThemeDTO;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import org.openide.util.Exceptions;
import service.ClientServiceController;
import service.ServiceConstant;
import util.Translator;

public abstract class IPanel extends JPanel
{

    private Translator translator;

    public IPanel()
    {
        try
        {
            translator = new Translator();
        }
        catch (NullPointerException ex)
        {

        }
    }

    public void startTask()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    public void stopTask()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    public void initGui()
    {
        ThemeDTO theme = ClientServiceController.getInstance().getUser().getUserSettings().getTheme();
        Color bgColor = ThemeDTO.toColor(theme.getBgColor());
        this.setBackground(bgColor);
    }

    public void setIcon(JLabel label)
    {
        label.setIcon(new ImageIcon(ServiceConstant.getInstance().getApplicationImage(ImageTypeDTO.LOGO)));
    }

    public void setError(JLabel label, String error)
    {
        label.setForeground(Color.red);
        label.setText(error);
    }

    public String translate(String messsage)
    {
        return translator.translate(messsage);
    }

    public void setLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex)
        {
            Exceptions.printStackTrace(ex);
        }
    }

    public void setBorder(JPanel panel)
    {
        ThemeDTO theme = ClientServiceController.getInstance().getUser().getUserSettings().getTheme();
        Color maleColor = ThemeDTO.toColor(theme.getMaleColor());
        panel.setBorder(new MatteBorder(0, 5, 0, 0, maleColor));
    }
}
