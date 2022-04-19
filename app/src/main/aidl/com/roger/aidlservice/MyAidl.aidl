// MyAidl.aidl
package com.roger.aidlservice;

//注意 Java中的包名路径必须与aidl中的包名路径一致。
//例如 java包中Person的路径为com.roger.binder.data.Person
//那么 aidl包中的Person的路径也必须为 com.roger.binder.data.Person
import com.roger.aidlservice.data.Person;

interface MyAidl {
    void addPerson(in Person person);

    List<Person> getPersonList();
}