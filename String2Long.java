
public class String2Long {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
		
    }
	
    public static Long  transferString2Long(String str) throws ExecutionException, InterruptedException {
        if(null == str || "".equals(str.trim())){
            return null;
        }

        if(str.endsWith("L")|| str.endsWith("l")){
            str = str.substring(0,str.length()-1);
        }

        String minValue = String.valueOf(Long.MIN_VALUE).substring(1,String.valueOf(Long.MIN_VALUE).length()) ;
        String maxValue = String.valueOf(Long.MAX_VALUE).substring(0,String.valueOf(Long.MAX_VALUE).length());

        if(str.startsWith("-")){
            str = str.substring(1,str.length());
            if(str.length() > minValue.length()){
                return null;
            }else if(str.length() == minValue.length()){
                for(int i = 0; i < minValue.length();i++){
                    if(str.charAt(i) > minValue.charAt(i)){
                        return null;
                    }
                }
                return (-1) * twoThreadHandle(str);
            }else{
                return (-1) * twoThreadHandle(str);
            }
        }else{
            if(str.length() > maxValue.length()){
                return null;
            }else if(str.length() == maxValue.length()){
                for(int i = 0;i < maxValue.length();i++){
                    if(str.charAt(i) > maxValue.charAt(i)){
                        return null;
                    }
                }
                return twoThreadHandle(str);
            }else{
                return twoThreadHandle(str);
            }
        }
    }
    public static Long twoThreadHandle(String str) throws ExecutionException, InterruptedException {
        String first = str.substring(0, str.length()/2);
        String latter = str.substring(str.length()/2, str.length());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,2,1000L,TimeUnit.SECONDS,new ArrayBlockingQueue<>(1));

        FutureTask<Long> firstFutureTask = null;
        firstFutureTask =(FutureTask) executor.submit(()->{
            return getLong(first);
        });
        FutureTask<Long> latterfutureTask = null;
        latterfutureTask = (FutureTask) executor.submit(() -> {
            return getLong(latter);
        });

        Long pre = (long) firstFutureTask.get();
        Long late = (long) latterfutureTask.get();
        if(pre == null || late == null){
            return null;
        }

        long n = 1;
        for(int i = 0;i< latter.length();i++){
            n *= 10;
        }
        return pre * n + late;
    }

    public static Long getLong(String str){
        if(str == null || "".equals(str)){
            return null;
        }
        long result = 0L;
        for(int i = str.length() - 1 ;i >= 0;i --){
            long num = str.charAt(i) - '0';
            if(num < 0 || num > 9){ return null;}
            result +=  num * (long)Math.pow(10,str.length() - i -1);
        }
        return result;
    }
}
