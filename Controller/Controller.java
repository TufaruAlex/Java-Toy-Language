package Controller;

import Model.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    ExecutorService executor;

    public IRepository getRepo() {
        return repo;
    }

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void allStep() throws IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrgCL(repo.getProgramStates());
        while (prgList.size() > 0) {
            List<Integer> symTableAddresses = Objects.requireNonNull(prgList.stream()
                            .map(p -> getAddrFromSymTable(p.getSymTable().getContent().values()))
                            .map(Collection::stream)
                            .reduce(Stream::concat).orElse(null))
                    .collect(Collectors.toList());
            prgList.forEach(prg -> prg.getHeap().setContent(safeGarbageCollector(symTableAddresses, getAddrFromHeap(prg.getHeap()
                    .getContent().values()), prg.getHeap().getContent())));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrgCL(repo.getProgramStates());
        }
        executor.shutdownNow();
        repo.setProgramStates(prgList);
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream().filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream().filter(v -> v instanceof RefValue).map(v -> {
            RefValue v1 = (RefValue) v;
            return v1.getAddress();
        }).collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream().filter(v -> v instanceof RefValue).map(v ->
                ((RefValue) v).getAddress()).collect(Collectors.toList());
    }

    public List<PrgState> removeCompletedPrgGUI(List<PrgState> inPrgList) {
        List<PrgState> uncompletedPrograms = inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
        if (uncompletedPrograms.isEmpty() && !repo.getProgramStates().isEmpty())
            uncompletedPrograms.add(repo.getProgramStates().get(0));
        return uncompletedPrograms;
    }

    public List<PrgState> removeCompletedPrgCL(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        });
        repo.setProgramStates(prgList);
    }

    public void oneStepAll() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrgGUI(repo.getProgramStates());

        List<Integer> symTableAddresses = Objects.requireNonNull(prgList.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().getContent().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        prgList.forEach(prg -> prg.getHeap().setContent(safeGarbageCollector(symTableAddresses, getAddrFromHeap(prg.getHeap()
                .getContent().values()), prg.getHeap().getContent())));
        oneStepForAllPrg(prgList);
        prgList = removeCompletedPrgGUI(repo.getProgramStates());

        executor.shutdownNow();
        repo.setProgramStates(prgList);
    }
}
