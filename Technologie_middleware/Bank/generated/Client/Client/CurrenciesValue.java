// **********************************************************************
//
// Copyright (c) 2003-2018 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.1
//
// <auto-generated>
//
// Generated from file `client.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Client;

public class CurrenciesValue implements java.lang.Cloneable,
                                        java.io.Serializable
{
    public float value;

    public float value2;

    public CurrenciesValue()
    {
    }

    public CurrenciesValue(float value, float value2)
    {
        this.value = value;
        this.value2 = value2;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        CurrenciesValue r = null;
        if(rhs instanceof CurrenciesValue)
        {
            r = (CurrenciesValue)rhs;
        }

        if(r != null)
        {
            if(this.value != r.value)
            {
                return false;
            }
            if(this.value2 != r.value2)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::Client::CurrenciesValue");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, value);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, value2);
        return h_;
    }

    public CurrenciesValue clone()
    {
        CurrenciesValue c = null;
        try
        {
            c = (CurrenciesValue)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeFloat(this.value);
        ostr.writeFloat(this.value2);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.value = istr.readFloat();
        this.value2 = istr.readFloat();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, CurrenciesValue v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public CurrenciesValue ice_read(com.zeroc.Ice.InputStream istr)
    {
        CurrenciesValue v = new CurrenciesValue();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<CurrenciesValue> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, CurrenciesValue v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            ostr.writeSize(8);
            ice_write(ostr, v);
        }
    }

    static public java.util.Optional<CurrenciesValue> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            istr.skipSize();
            return java.util.Optional.of(CurrenciesValue.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final CurrenciesValue _nullMarshalValue = new CurrenciesValue();

    public static final long serialVersionUID = 1196453514L;
}
