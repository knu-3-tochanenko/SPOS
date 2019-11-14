import java.util.Random;
import java.util.Vector;
import java.io.*;
import java.util.ArrayList;

public class Lottery {
    public static Results run(int runtime, Vector processVector, Results result) {
        int i = 0;
		int comptime = 0;
		int currentProcess = 0;
		int previousProcess = 0;
		int size = processVector.size();
		int completed = 0;
        String resultsFile = "Summary-Processes";
        
        Random random = new Random();

		result.schedulingType = "Batch (Nonpreemptive)";
		result.schedulingName = "Lottery";
		try {
			// BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
			// OutputStream out = new FileOutputStream(resultsFile);
			PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
			sProcess process = (sProcess) processVector.elementAt(currentProcess);
			out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking
					+ " " + process.cpudone + " " + process.cpudone + ")");
			while (comptime < runtime) {
				if (process.cpudone == process.cputime) {
					completed++;
					out.println("Process: " + currentProcess + " completed... (" + process.cputime + " "
							+ process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
					if (completed == size) {
						result.compuTime = comptime;
						out.close();
						return result;
					}

					ArrayList<Integer> list = new ArrayList<Integer>();
					for (int j = 0; j < size; j++) {
						process = (sProcess) processVector.elementAt(j);
						if (process.cpudone < process.cputime)
							list.add(j);
					}

					if (list.size() == 1)
						continue;
                    
                    while (true) {
                        int value = random.nextInt(list.size());
						process = (sProcess) processVector.elementAt(list.get(value));
                        if (process.cpudone < process.cputime) {
                            currentProcess = list.get(value);
                            break;
                        }
                    }

                    ///////////////////////////////////////////////////////

					process = (sProcess) processVector.elementAt(currentProcess);
					out.println("Process: " + currentProcess + " registered... (" + process.cputime + " "
							+ process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
				}
				if (process.ioblocking == process.ionext) {
					out.println("Process: " + currentProcess + " I/O blocked... (" + process.cputime + " "
							+ process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
					process.numblocked++;
					process.ionext = 0;
					previousProcess = currentProcess;
					
					
					ArrayList<Integer> list = new ArrayList<Integer>();
					for (int j = 0; j < size; j++) {
						process = (sProcess) processVector.elementAt(j);
						if (process.cpudone < process.cputime)
							list.add(j);
					}

					if (list.size() == 1)
						continue;

                    while (true) {
                        int value = random.nextInt(list.size());
						process = (sProcess) processVector.elementAt(list.get(value));
                        if (process.cpudone < process.cputime && previousProcess != value) {
							currentProcess = list.get(value);
							break;
                        }
                    }
                    

					process = (sProcess) processVector.elementAt(currentProcess);
					out.println("Process: " + currentProcess + " registered... (" + process.cputime + " "
							+ process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
				}
				process.cpudone++;
				if (process.ioblocking > 0) {
					process.ionext++;
				}
				comptime++;
			}
			out.close();
		} catch (IOException e) {
			/* Handle exceptions */ }
		result.compuTime = comptime;
		return result;
    }
}