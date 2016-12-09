/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search_engine;

import entity.Airline;
import facades.AirlineFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bancho
 */
public class SearchEngine {
    
    private AirlineFacade af;
    
    public SearchEngine(){
        af = new AirlineFacade();
    }
    
    public List<String> findFlights(String from, String date, String numTickets){
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Airline> airlines = af.getAllAirlines();
        List<Future> futures = new ArrayList<Future>();
        
        String apiPath = "/api/flightinfo/" + from + "/" + date + "/" + numTickets;
        
        for (Airline airline : airlines) {
            Callable<String> task = new SearchTask(airline.getUrl() + apiPath);
            futures.add(executor.submit(task));
        }
        
        List<String> results = new ArrayList<String>();
        
        for (Future future : futures) {
            try {
                String result = (String) future.get();
                if (result != null) {
                    results.add(result);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        executor.shutdown();
        
        return results;
    }
    
    public List<String> findFlights(String from, String to, String date, String numTickets){
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Airline> airlines = af.getAllAirlines();
        List<Future> futures = new ArrayList<Future>();
        
        String apiPath = "/api/flightinfo/" + from + "/" + to + "/" + date + "/" + numTickets;
        
        for (Airline airline : airlines) {
            Callable<String> task = new SearchTask(airline.getUrl() + apiPath);
            futures.add(executor.submit(task));
        }
        
        List<String> results = new ArrayList<String>();
        
        for (Future future : futures) {
            try {
                String result = (String) future.get();
                if (result != null) {
                    results.add(result);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        executor.shutdown();
        
        return results;
    }
}
