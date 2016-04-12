
import java.sql.Timestamp;

public class DateTime {
	public int Y, M, D, h, m, s;

	public DateTime(int Y, int M, int D, int h, int m, int s) {
		this.Y = Y;
		this.M = M;
		this.D = D;
		this.h = h;
		this.m = m;
		this.s = s;
	}

	public DateTime(Timestamp ts) {
		if (ts == null) {
			this.Y = 0;
			this.M = 0;
			this.D = 0;
			this.h = 0;
			this.m = 0;
			this.s = 0;
		} else {

			String s1 = ts.toString();
			int[] arr = new int[7];
			int j = 0;

			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) >= '0' && s1.charAt(i) <= '9') {
					arr[j] *= 10;
					arr[j] += Integer.parseInt(s1.charAt(i) + "");
				} else {
					j++;
				}
			}

			this.Y = arr[0];
			this.M = arr[1];
			this.D = arr[2];
			this.h = arr[3];
			this.m = arr[4];
			this.s = arr[5];
		}
	}

	public static boolean diffMoreThanXDays(DateTime dp1, DateTime dp2, int diff) {
		return (Math.abs((dp1.sumDays() - dp2.sumDays())) > diff);
	}

	public int sumDays() {
		return (Y * 365 + M * 30 + D);
	}

	public void printDate() {
		System.out.println(D + "/" + M + "/" + Y);
	}

	public void printTime() {
		System.out.println(h + ":" + m + ":" + s);
	}

	public void printDateTime() {
		System.out.print(D + "/" + M + "/" + Y + " ");
		System.out.println(h + ":" + m + ":" + s);
	}
	
	public String toString() {
		return String.format("%d/%d/%d %d:%d", D, M, Y, h, m);
	}
}
