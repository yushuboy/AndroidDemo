package com.axaet.ipc.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.axaet.ipc.beans.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * date: 2019/3/18
 *
 * @author yuShu
 */
public class SerializeUtil {

    private static final String TAG = "SerializeUtil";

    /**
     * 对象序列化
     * @param context 上下文
     */
    public static void serializePerson(Context context) {
        Person person = new Person(18, "yuShu", "1111");
        FileOutputStream stream = null;
        ObjectOutputStream out = null;
        File file = new File(context.getExternalCacheDir(), "Person.txt");
        try {
            stream = new FileOutputStream(file);
            out = new ObjectOutputStream(stream);
            out.writeObject(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Toast.makeText(context, "路径: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
    }
    /**
     * 对象反序列化
     * @param context 上下文
     */
    public static void unSerializePerson(Context context) {
        FileInputStream stream = null;
        ObjectInputStream in = null;
        try {
            stream = new FileInputStream(new File(context.getExternalCacheDir(), "Person.txt"));
            in = new ObjectInputStream(stream);
            Person person = (Person) in.readObject();
            Toast.makeText(context, "person:" + person, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "unSerializePerson: " + person.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
