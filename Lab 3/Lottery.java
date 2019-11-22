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
			SchedulingProcess process = (SchedulingProcess) processVector.elementAt(currentProcess);
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

					currentProcess = getNextProcess(size, processVector, random, previousProcess, currentProcess, false);

					///////////////////////////////////////////////////////

					process = (SchedulingProcess) processVector.elementAt(currentProcess);
					out.println("Process: " + currentProcess + " registered... (" + process.cputime + " "
							+ process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
				}
				if (process.ioblocking == process.ionext) {
					out.println("Process: " + currentProcess + " I/O blocked... (" + process.cputime + " "
							+ process.ioblocking + " " + process.cpudone + " " + process.cpudone + ")");
					process.numblocked++;
					process.ionext = 0;
					previousProcess = currentProcess;

					currentProcess = getNextProcess(size, processVector, random, previousProcess, currentProcess, true);

					process = (SchedulingProcess) processVector.elementAt(currentProcess);
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

	// Gets new item based on random values
	private static int getNextProcess(int size, Vector<SchedulingProcess> processVector, Random random,
			int previousProcess, int currentProcess, boolean checkForPrev) {
		SchedulingProcess process;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < size; j++) {
			process = (SchedulingProcess) processVector.elementAt(j);
			if (process.cpudone < process.cputime)
				list.add(j);
		}

		while (true) {
			int value = random.nextInt(list.size());
			process = (SchedulingProcess) processVector.elementAt(list.get(value));
			if (process.cpudone < process.cputime && ((checkForPrev) ? previousProcess != value : true)) {
				currentProcess = list.get(value);
				break;
			}
		}
		return currentProcess;
	}
}