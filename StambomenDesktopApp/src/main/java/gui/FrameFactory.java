package gui;

import javax.swing.JFrame;

public class FrameFactory
{

    private static JFrame frame;

    public static JFrame makeFrame(Frames frameType)
    {
        if (frame != null)
        {
            setInvisible(frame);
        }

        switch (frameType)
        {
            case LOGIN:
                frame = new Login();
                break;
            case REGISTER:
                frame = new Register();
                break;
        }

        setVisible(frame);
        return frame;
    }

    private static void setVisible(JFrame frame)
    {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void setInvisible(JFrame frame)
    {
        frame.setVisible(false);
    }
}
