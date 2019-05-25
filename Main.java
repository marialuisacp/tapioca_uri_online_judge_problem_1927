import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import java.util.Arrays;

public class Main {
	public static String inputFile = "input.txt";
	public static long[] trufa;
	public static long quantidade_trufas = 0;
	public static ArrayList<Trufa> trufas = new ArrayList<Trufa>(3000);
	public static Trufa n_trufa;
	public static long[][][] save = new long[21][21][1050];

	public static void main(String[] args) throws IOException {
		trufa = new long[3];
		readFiles();
	}
	public static void readFiles(){
		long 	l = 0, number = 0, pos = 0, p = 1, sm = 0;
		try {
			// Scanner scanner = new Scanner(System.in);  
			Scanner scanner = new Scanner(new FileReader(inputFile));
			long[] 	par = new long[(int)2];
			long n1 = 0, n2 = 0;
			while(scanner.hasNextInt()) {
				number = scanner.nextInt();
				if(l == pos){
					if(l > 0) tenta();
					quantidade_trufas = number;
					pos = pos + (number*3) + 1;
					save = new long[21][21][1050];
					trufas = new ArrayList<Trufa>(3000);
				}else{
					if( (pos - l) % 3 == 0){ n1 = number; }
					if( (pos - l) % 3 == 2){ n2 = number; }
					if( (pos - l) % 3 == 1){
						n_trufa = new Trufa(n1, n2, number);
						trufas.add(n_trufa);
					}
				}
				l++;
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
		}
		tenta();
	}
	public static void tenta(){
		for (int j = 0; j < 21; j++){
			for (int x = 0; x < 21; x++){
				for (int i = 0; i < 1050; i++){
					save[j][x][i] = -1;
				}
			}
		}
		Trufa[] array_trufas = new Trufa[3000];
		Collections.sort(trufas);
		for (int i = 0; i < quantidade_trufas; i++){
			array_trufas[i] = trufas.get(i);
			// System.out.println(array_trufas[i].x + " " + array_trufas[i].y + " " + array_trufas[i].t);
		}
		long resultado = verifica(6, 6, 0, array_trufas, quantidade_trufas);
		System.out.println(resultado);
	}

	public static boolean podePegar(Trufa t, long x, long y, long tp){
		if(t.t == tp && t.x == x && t.y == y)
			return true;
		return false;
	}

	public static long verifica(long x, long y, long t, Trufa[] t_r, long q){
				
		long p = 0;

		if(t < 0 || t > 1000 || x < 0 || y < 0 || x > 20 || y > 20)
			return 0;	
		
		if(save[(int)x][(int)y][(int)t] != -1){
			return save[(int)x][(int)y][(int)t];
		}

		if(q == 1){
			if(t_r[(int)q-1].x == x && t_r[(int)q-1].y == y && t_r[(int)q-1].t == t){
				return 1;
			}
			else{
				return 0;
			}
		}
		if(q == 0){
			return 0;
		}

		// System.out.println(t_r[0].x + " " + t_r[0].y + " " + t_r[0].t);

		if(t_r[(int)q-1].t == t){
			if(q > 1 && q < 3000){
				long new_q = q;

				while((new_q > 0) && (t_r[(int)new_q-1].t == t) && (new_q <= q)){
					if((t_r[(int)new_q-1].t == t) && (t_r[(int)new_q-1].x == x) && (t_r[(int)new_q-1].y == y)){
						p = p + 1;
					}
					new_q--;
				}
				q = new_q;
			}
		}


		long r1 = verifica(x+1, y, t+1, t_r, q);
		long r2 = verifica(x-1, y, t+1, t_r, q);
		long r3 = verifica(x, y+1, t+1, t_r, q);
		long r4 = verifica(x, y-1, t+1, t_r, q);
		long r5 = verifica(x, y, t+1, t_r, q);

		save[(int)x][(int)y][(int)t] = (long) ((long) (p + (long)((Math.max((long)r1, Math.max((long)r2, Math.max(r3, Math.max((long)r4, (long)r5))))))));
		return save[(int)x][(int)y][(int)t];
	}

	public static class Trufa implements Comparable<Trufa>{
		long x;
		long y;
		long t;
		public Trufa(long x, long y, long t){
			this.x = x;
			this.y = y;
			this.t = t;
		}
		@Override
		public int compareTo(Trufa p_trufa) {
			if(this.t > p_trufa.getTime())
				return -1;
			if(this.t < p_trufa.getTime())
				return 1;
			return 0;
		}
		public long getTime(){
			return this.t;
		}
	}

}