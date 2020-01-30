package by.javatr.financetracker.factory;

import by.javatr.financetracker.service.ClientService;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.impl.ClientServiceImpl;
import by.javatr.financetracker.service.impl.FinanceTrackerServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final ClientService clientServiceImpl = new ClientServiceImpl();
    private final FinanceTrackerService financeTrackerServiceImpl = new FinanceTrackerServiceImpl();

    private ServiceFactory(){

    }

    public static ServiceFactory getInstance(){
        return instance;
    }
    public ClientService getClientService() {
        return clientServiceImpl;
    }

    public FinanceTrackerService getFinanceTrackerService(){
        return financeTrackerServiceImpl;
    }

}
