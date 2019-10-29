
package antrian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuizAlgodat {
    static int top;
    static void menu()
{
        System.out.println("1.Masukkan data antrian");
        System.out.println("2.Display");
        System.out.println("3.Mencari");
        System.out.println("4.Keluarkan1 data pertama");
        System.out.println("5.Keluar");
        System.out.print("Pilih Menu = ");
        int a=input();
        if (a==1)
        {
            InputnPQ();
        }
        else if (a==2)
        {
            display();
        }
        else if (a==3)
        {
            search();
        }
        else if (a==4)
        {
            Outantri();
        }
        else if (a==5)
        {
            System.exit(0);
        }
        else
        {
            System.out.println("Angka yang anda masukkan salah");
        }
}

    static int input()
    {
	BufferedReader a=new BufferedReader (new InputStreamReader(System.in));
	String kata=null;
	try
	{
		kata=a.readLine();

	}catch(IOException e)
	{
		e.toString();
	}
	int nilai= Integer.valueOf(kata).intValue();
	return nilai;
    }

static int arr[];
static void InputnPQ()
{
        arr=new int[5];
        top=0;
        System.out.println("masukkan angka");
        for (int i=0;i<arr.length;i++)
	{
            System.out.print("pengantri ke "+(i+1)+" = ");
            int j,z;
            z=input();
            for (j=0;j<top;j++)
            {
                if (arr[j]>=z)
                {
                    break;
                }
            }
            for (int k=top;k>=j;k--)
            {
                if (k==arr.length-1)
                {
                    continue;
                }
                else
                {
                    arr[k+1]=arr[k];
                }
            }
            arr[j]=z;
            top++;
	}
        menu();
}
static void Outantri()
{
    for (int i=0;i<arr.length;i++)
    {
        if (i==arr.length-1)
        {
            arr[i]=0;
            continue;
        }
        else
        {
            arr[i]=arr[i+1];
        }
    }
    System.out.println("Pengantri dengan nilai terkecil yang berada pada indeks terkecil telah dikeluarkan");
    
    menu();
}
static void search()
{
    int x=arr.length;
    int i;
    System.out.print("Masukkan angka yang anda ingin cari = ");
    int b=input();
    for(i=0;i<x;i++)
    {
        if(b==arr[i])
        {
            break;
        }
    }
    System.out.println("\nAngka yang anda cari dari antrian berada pada indeks ke : "+i);
    menu();
}
static void display()
{
    System.out.println("Daftar pengantri :");
    for (int i=0;i<arr.length;i++)
    {
        if (arr[i]==0)
        {
            continue;
        }
        else
        {
        System.out.println("Data pada indeks ke "+i+" = "+arr[i]);
        }
    }
    if (arr[0]==0)
    {
        System.out.println("Antrian Kosong");
    }
    menu();
}    

    public static void main(String[] args) {
                System.out.println("\t\tALGORITMA DAN STRUKTUR DATA\n");
        menu();

    }
    
}
