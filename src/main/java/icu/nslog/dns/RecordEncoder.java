package icu.nslog.dns;

/**
 * @className: RecordEncoder
 * @description: TODO
 * @author: cookun
 * @date: 1/14/22
 **/
public interface RecordEncoder {

    String decrypt(String s);

    String encryption(String s);
}
