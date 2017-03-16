package com.mgaga.starterapp.helpers;

/**
 * Created by tshepomgaga on 2017/03/14.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class KeyVal<A, B> implements Comparable<KeyVal<A, B>>, Parcelable {
    public static final Creator<KeyVal> CREATOR = new Creator() {
        public KeyVal createFromParcel(Parcel in) {
            return new KeyVal(in);
        }

        public KeyVal[] newArray(int size) {
            return new KeyVal[size];
        }
    };
    private A key;
    private B value;

    public KeyVal(A aKey, B aVal) {
        this.key = aKey;
        this.value = aVal;
    }

    protected KeyVal(Parcel in) {
    }

    public A getKey() {
        return this.key;
    }

    public B getValue() {
        return this.value;
    }

    public int compareTo(KeyVal<A, B> another) {
        return this.key instanceof Comparable?((Comparable)this.key).compareTo(another.key):0;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            KeyVal keyVal = (KeyVal)o;
            if(this.key != null) {
                if(!this.key.equals(keyVal.key)) {
                    return false;
                }
            } else if(keyVal.key != null) {
                return false;
            }

            boolean var10000;
            label51: {
                if(this.value != null) {
                    if(!this.value.equals(keyVal.value)) {
                        break label51;
                    }
                } else if(keyVal.value != null) {
                    break label51;
                }

                var10000 = true;
                return var10000;
            }

            var10000 = false;
            return var10000;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.key != null?this.key.hashCode():0;
        result = 31 * result + (this.value != null?this.value.hashCode():0);
        return result;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable((Serializable)this.key);
        parcel.writeSerializable((Serializable)this.value);
    }
}