package so.simulator.controllers;

import so.simulator.models.Process;
import so.simulator.models.ProcessStateManager;
import so.simulator.models.exceptions.CPUException;
import so.simulator.views.GuiManager;
import so.util.observer.Observer;
import so.util.observer.ObserverEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener, Observer {

    private GuiManager guiManager;
    private ProcessStateManager stateManager;

    public Controller() {
        this.guiManager = new GuiManager(this);
        this.stateManager = new ProcessStateManager(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case Commands.BTN_STAR_UCP:
                int timeAssign = guiManager.getTimeAssignUCP();
                System.out.println(timeAssign);
                break;
            case Commands.BTN_CREATE_PROCESS:
                createProcess();
                guiManager.resetSpinner();
                break;

        }
    }

    private void createProcess() {
        int time = guiManager.getTimeNewProcess();
        stateManager.addProcess(time);
        guiManager.updateReadyQueue(stateManager.getReadyQueue());
        return;
    }

    @Override
    public void update(ObserverEvent event) {
        System.out.println(event);
        switch (event) {
            case UPDATE_TIME:
                updateTime();
                break;
            case BLOCK:
                blockProcess();
                break;
            case TIME_EXPIRATION:
                nextProcess();
                break;
        }
    }

    /**
     * Actualiza los tiempos restantes de ejecución
     */
    private void updateTime() {
        int ucpTime = stateManager.getCPUTimeRemaining();
        guiManager.setTimeRestUCP(ucpTime);
        int processTime = stateManager.getProcessTimeRemaining();
        guiManager.setTimeAssignUCP(processTime);
    }

    private void blockProcess() {
        //Bloquea el proceso de la UCP
        stateManager.blockProcess();
        try {
            // TODO: 14/12/21 Notificar si no hay mas procesos
            if (stateManager.hasProcessesReady()) {
                stateManager.dispatchNextProcess();
                Process process = stateManager.getRunningProcess();
                guiManager.setProcessActual(
                        process.getProcessName(),
                        process.getSecondsOfExecution(),
                        process.getSecondsOfExecutionRemaining());
                updateListAndQueue();
            }
        } catch (CPUException e) {
            e.printStackTrace();
        }
    }

    private void nextProcess() {
        try {
            Process process = stateManager.finishProcessTurn();
            guiManager.setProcessActual(
                    process.getProcessName(),
                    process.getSecondsOfExecution(),
                    process.getSecondsOfExecutionRemaining()
            );
        } catch (CPUException e) {
            e.printStackTrace();
        }
    }

    private void updateListAndQueue() {
        guiManager.updateBlockedList(stateManager.getBlockedList());
        guiManager.updateReadyQueue(stateManager.getReadyQueue());
    }
}
