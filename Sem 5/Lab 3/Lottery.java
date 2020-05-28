import java.util.Random;
import java.util.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lottery {
	public static Results run(int runtime, Vector processVector, Results result, String resultsFile) {
		int i = 0;
		int comptime = 0;
		int currentProcess = 0;
		int previousProcess = 0;
		int size = processVector.size();
		int completed = 0;

		Random random = new Random();

		result.schedulingType = "Batch (Nonpreemptive)";
		result.schedulingName = "Lottery";
		try {
			// BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
			// OutputStream out = new FileOutputStream(resultsFile);
			PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
			SchedulingProcess process = (SchedulingProcess) processVector.elementAt(currentProcess);
			printProcess(currentProcess, process, "Registered", out);
			while (comptime < runtime) {
				if (process.getCpudone() == process.getCputime()) {
					completed++;
					printProcess(currentProcess, process, "Completed", out);

					if (completed == size) {
						result.compuTime = comptime;
						out.close();
						return result;
					}

					previousProcess = currentProcess;

					currentProcess = getNextProcess(size, processVector, random, previousProcess, currentProcess, false);

					///////////////////////////////////////////////////////

					process = (SchedulingProcess) processVector.elementAt(currentProcess);
					printProcess(currentProcess, process, "Registered", out);
				}
				if (process.getIoblocking() == process.getIonext()) {
					printProcess(currentProcess, process, "I/O blocked", out);
					process.increaseNumblocked();
					process.setIonext(0);
					previousProcess = currentProcess;

					currentProcess = getNextProcess(size, processVector, random, previousProcess, currentProcess, true);

					process = (SchedulingProcess) processVector.elementAt(currentProcess);
					printProcess(currentProcess, process, "Registered", out);
				}
				process.increaseCpudone();
				if (process.getIoblocking() > 0) {
					process.increaseIonext();
				}
				comptime++;
			}
			out.close();
		} catch (IOException e) {
			/* Handle exceptions */ }
		result.compuTime = comptime;
		return result;
	}

	private static void printProcess(int id, SchedulingProcess process, String action, PrintStream out) {
		out.println("Process: " + id + " " + action + "...  \t( " + process.getCputime() + " "
							+ process.getIoblocking() + " " + process.getCpudone() + " " + ")"
							+ (process.getCpudone() > process.getCputime() ? "\t\tERROR" : ""));
	}

	// Gets new item based on random values
	private static int getNextProcess(int size, Vector<SchedulingProcess> processVector, Random random,
			int previousProcess, int currentProcess, boolean checkForPrev) {
		SchedulingProcess process;
		List<Integer> list = new ArrayList<>();
		int currentProcessesNumber = 0;
		for (int j = 0; j < size; j++) {
			process = (SchedulingProcess) processVector.elementAt(j);
			if (process.getCpudone() < process.getCputime()) {
				currentProcessesNumber++;
				for (int k = 0; k < process.getPriority(); k++)
					list.add(j);
			}
		}

		if (currentProcessesNumber == 1)
			return list.get(0);

		while (true) {
			int value = random.nextInt(list.size());
			process = (SchedulingProcess) processVector.elementAt(list.get(value));
			if (process.getCpudone() < process.getCputime() && ((checkForPrev) ? previousProcess != value : true)) {
				currentProcess = list.get(value);
				break;
			}
		}
		return currentProcess;
	}
}