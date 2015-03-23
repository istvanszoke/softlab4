package inspector;
import java.util.HashMap;

public class Inspector {

    public static HashMap<Long, Integer> depthMap = new HashMap<Long, Integer>();
    public static HashMap<Long, Boolean> enableMap = new HashMap<Long, Boolean>();

    static public void setEnabled(boolean state)
    {
        long currentThread = Thread.currentThread().getId();
        enableMap.put(currentThread, state);
    }

    static public void call(String text)
    {
        long currentThread = Thread.currentThread().getId();
        if (enableMap.containsKey(currentThread)) {
            if (!enableMap.get(currentThread))
                return;
        } else {
            enableMap.put(currentThread, false);
            return;
        }
        int depth = 0;
        if ( Inspector.depthMap.containsKey(currentThread) ) {
            depth = Inspector.depthMap.get(currentThread) + 1;
            Inspector.depthMap.put(currentThread, depth);
        } else {
            Inspector.depthMap.put(currentThread, 0);
        }
        System.out.print( "> " );
        for (int i = 0; i < depth - 1; ++i) {
            System.out.print( "... " );
        }
        System.out.println( text );
    }

    static public void ret(String text)
    {
        long currentThread = Thread.currentThread().getId();
        if (enableMap.containsKey(currentThread)) {
            if (!enableMap.get(currentThread))
                return;
        } else {
            enableMap.put(currentThread, false);
            return;
        }
        int depth = 0;
        if ( Inspector.depthMap.containsKey( currentThread ) ) {
            depth = Inspector.depthMap.get( currentThread ) - 1;
            Inspector.depthMap.put( currentThread, depth < 0 ? 0 : depth);
        }
        System.out.print( "< " );
        for(int i = 0; i < depth; ++i) {
            System.out.print( "... " );
        }
        System.out.println( text );
    }

    static public void test(String testCaseName, String onTrueText, String onFalseText, boolean value)
    {
        System.out.print(testCaseName + " | ");
        if ( value ) {
            System.out.println( onTrueText );
        } else {
            System.out.println( onFalseText );
        }
    }
}