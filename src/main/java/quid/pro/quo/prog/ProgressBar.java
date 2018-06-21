package quid.pro.quo.prog;

/**
 * 
 */
public class ProgressBar
{
    // ----------------------------------------------------------------
    
    public static void main( String[] args ) throws Exception
    {         
        for (int i = 0; i <= 200; i = i + 20) {
            progressPercentage(i, 200);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
        }
    }

    // ----------------------------------------------------------------

    public static void progressPercentage( int remain, int total )
    {
        if (remain > total)
        {
            throw new IllegalArgumentException();
        }
        
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        
        for (int i = 0; i < remainProcent; i++)
        {
            bareDone.append(icon);
        }
        
        String bareRemain = bare.substring(remainProcent, bare.length());
        System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
        if (remain == total)
        {
            System.out.print("\n");
        }
    }    
    
}
