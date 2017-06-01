import org.junit.Test;

public class GenerateBarCodeTest {
    @Test
    public void test(){
        String latestBarCode = "B0370143";
        latestBarCode = latestBarCode.substring(1);
        int length = latestBarCode.length();
        Integer latestBarCodeInteger = Integer.parseInt(latestBarCode);
        String barCode = (latestBarCodeInteger + 1) + "";
        StringBuilder sb = new StringBuilder();
        if (barCode.length() < length) {
            for (int i = 0; i < length - barCode.length(); i++) {
                sb.append(0);
            }
        }
        barCode = sb.toString() + barCode;
        System.out.println(barCode);
    }
}
