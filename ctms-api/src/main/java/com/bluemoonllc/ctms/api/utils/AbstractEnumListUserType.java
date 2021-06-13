package com.bluemoonllc.ctms.api.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractEnumListUserType implements UserType {

    protected static final int[] SQL_TYPES = {Types.ARRAY};

    protected abstract Object mapValues(Stream<String> stringStream);

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return List.class;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (Objects.isNull(value)) {
            preparedStatement.setNull(index, SQL_TYPES[0]);
        } else {
            List list = (List) value;
            Enum[] castObject = (Enum[]) list.toArray(new Enum[0]);
            Array array = sharedSessionContractImplementor.connection().createArrayOf("varchar", castObject);
            preparedStatement.setArray(index, array);
        }
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names,
                              SharedSessionContractImplementor sharedSessionContractImplementor, Object owner) throws HibernateException, SQLException {
        Array sqlArray;
        Object objectArray;
        if (resultSet.wasNull() || names.length == 0 || ((sqlArray = resultSet.getArray(names[0])) == null) ||
                ((objectArray = sqlArray.getArray()) == null)) {
            return null;
        }
        return mapValues(Arrays.stream((String[]) objectArray));
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Object assemble(Serializable serializable, Object value) throws HibernateException {
        return value;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public boolean equals(Object value, Object value1) throws HibernateException {
        if (value == value1) {
            return true;
        } else if (value == null || value1 == null) {
            return false;
        } else {
            return value.equals(value1);
        }
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
