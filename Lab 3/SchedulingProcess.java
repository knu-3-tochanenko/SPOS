public class SchedulingProcess {
	public int cputime;
	public int ioblocking;
	public int cpudone;
	public int ionext;
	public int numblocked;

	public SchedulingProcess(int cputime, int ioblocking, int cpudone, int ionext, int numblocked) {
		this.cputime = cputime;
		this.ioblocking = ioblocking;
		this.cpudone = cpudone;
		this.ionext = ionext;
		this.numblocked = numblocked;
	}
}
