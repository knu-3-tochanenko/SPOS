public class SchedulingProcess {
	private int cputime;
	private int ioblocking;
	private int cpudone;
	private int ionext;
	private int numblocked;

	public SchedulingProcess(int cputime, int ioblocking, int cpudone, int ionext, int numblocked) {
		this.cputime = cputime;
		this.ioblocking = ioblocking;
		this.cpudone = cpudone;
		this.ionext = ionext;
		this.numblocked = numblocked;
	}

	public int getCputime() {
		return cputime;
	}

	public int getIoblocking() {
		return ioblocking;
	}

	public int getCpudone() {
		return cpudone;
	}

	public int getIonext() {
		return ionext;
	}

	public int getNumblocked() {
		return numblocked;
	}

	public void addCpudone(int time) {
		cpudone += time;
	}

	public void increaseCpudone() {
		cpudone++;
	}

	public void increaseIonext() {
		ionext++;
	}

	public void setIonext(int next) {
		ionext = next;
	}

	public void increIoblocking() {
		ioblocking++;
	}

	public void increaseNumblocked() {
		numblocked++;
	}
}
