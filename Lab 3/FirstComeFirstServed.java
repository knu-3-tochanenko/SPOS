// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class FirstComeFirstServed {

	public static Results run(int runtime, Vector processVector, Results result) {
		int i = 0;
		int comptime = 0;
		int currentProcess = 0;
		int previousProcess = 0;
		int size = processVector.size();
		int completed = 0;
		String resultsFile = "Summary-Processes";

		result.schedulingType = "Batch (Nonpreemptive)";
		result.schedulingName = "First-Come First-Served";
		try {
			// BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
			// OutputStream out = new FileOutputStream(resultsFile);
			PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
			SchedulingProcess process = (SchedulingProcess) processVector.elementAt(currentProcess);
			out.println("Process: " + currentProcess + " registered... (" + process.getCputime() + " " + process.getIoblocking()
					+ " " + process.getCpudone() + " " + process.getCpudone() + ")");
			while (comptime < runtime) {
				if (process.getCpudone() == process.getCputime()) {
					completed++;
					out.println("Process: " + currentProcess + " completed... (" + process.getCputime() + " "
							+ process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");
					if (completed == size) {
						result.compuTime = comptime;
						out.close();
						return result;
					}
					for (i = size - 1; i >= 0; i--) {
						process = (SchedulingProcess) processVector.elementAt(i);
						if (process.getCpudone() < process.getCputime()) {
							currentProcess = i;
						}
					}
					process = (SchedulingProcess) processVector.elementAt(currentProcess);
					out.println("Process: " + currentProcess + " registered... (" + process.getCputime() + " "
							+ process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");
				}
				if (process.getIoblocking() == process.getIonext()) {
					out.println("Process: " + currentProcess + " I/O blocked... (" + process.getCputime() + " "
							+ process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");
					process.increaseNumblocked();
					process.setIonext(0);
					previousProcess = currentProcess;
					for (i = size - 1; i >= 0; i--) {
						process = (SchedulingProcess) processVector.elementAt(i);
						if (process.getCpudone() < process.getCputime() && previousProcess != i) {
							currentProcess = i;
						}
					}
					process = (SchedulingProcess) processVector.elementAt(currentProcess);
					out.println("Process: " + currentProcess + " registered... (" + process.getCputime() + " "
							+ process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");
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
}
