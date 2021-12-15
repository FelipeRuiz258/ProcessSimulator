package so.simulator.views.panels;

import so.simulator.controllers.Commands;
import so.simulator.views.Constants;
import so.simulator.views.components.MyJButton;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PanelCreateProcess extends JPanel {

    private JLabel labelNameProcess;
    private JTextField textFieldNameProcess;
    private JLabel labelTextTimeProcess;
    private JSpinner spinnerSegProcess;
    private JLabel labelSeg;
    private MyJButton btnCreateProcess;

    public PanelCreateProcess(ActionListener listener) {
        setBorder(BorderFactory.createTitledBorder(Constants.TITTLE_PANEL_CREATION_PROCESS));
        init(listener);

    }

    private void init(ActionListener listener) {
        this.labelNameProcess = new JLabel(Constants.TEXT_LABEL_NAME_PROCESS);
        this.textFieldNameProcess = new JTextField();
        this.textFieldNameProcess.setEnabled(false);
        this.labelTextTimeProcess = new JLabel(Constants.TEXT_LABEL_TIME_PROCESS);
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        spinnerNumberModel.setMinimum(1);
        spinnerNumberModel.setMaximum(300);
        this.spinnerSegProcess = new JSpinner(spinnerNumberModel);
        spinnerSegProcess.setValue(1);
        this.labelSeg = new JLabel(Constants.TEXT_SEG);
        this.btnCreateProcess = new MyJButton(listener, Commands.BTN_CREATE_PROCESS, Constants.TEXT_BTN_CREATE_PROCESS);
        this.setEnableComponents(false);
        fill();
    }

    private void fill() {
        this.setLayout(null);
        this.labelNameProcess.setBounds(20, 20, 60, 20);
        this.textFieldNameProcess.setBounds(80, 20, 90, 30);
        this.labelTextTimeProcess.setBounds(20, 50, 60, 20);
        this.spinnerSegProcess.setBounds(80, 50, 90, 30);
        this.labelSeg.setBounds(175, 55, 60, 20);
        this.btnCreateProcess.setBounds(210, 20, 130, 60);
        add(labelNameProcess);
        add(textFieldNameProcess);
        add(labelTextTimeProcess);
        add(spinnerSegProcess);
        add(labelSeg);
        add(btnCreateProcess);
    }

    public void setEnableComponents(boolean status) {
        this.setEnabled(status);
        this.spinnerSegProcess.setEnabled(status);
        this.btnCreateProcess.setEnabled(status);
    }

    public int getNameProcess() {
        return Integer.parseInt(this.textFieldNameProcess.getText());
    }

    public int getTimeProcess() {
        return (int) spinnerSegProcess.getValue();
    }

    public void resetSpinner() {
        this.spinnerSegProcess.setValue(1);
    }
}
