package org.embulk.output.sqlserver.setter;

import java.sql.Types;

import org.embulk.output.jdbc.BatchInsert;
import org.embulk.output.jdbc.JdbcColumn;
import org.embulk.output.jdbc.JdbcColumnOption;
import org.embulk.output.jdbc.setter.ColumnSetter;
import org.embulk.output.jdbc.setter.ColumnSetterFactory;
import org.joda.time.DateTimeZone;

public class SQLServerColumnSetterFactory
        extends ColumnSetterFactory
{
    public SQLServerColumnSetterFactory(BatchInsert batch, DateTimeZone defaultTimeZone)
    {
        super(batch, defaultTimeZone);
    }

    @Override
    public ColumnSetter newCoalesceColumnSetter(JdbcColumn column, JdbcColumnOption option)
    {
        switch (column.getSqlType()) {
        case Types.TINYINT:
            return new SQLServerByteColumnSetter(batch, column, newDefaultValueSetter(column, option));

        case Types.TIME:
            return new SQLServerSqlTimeColumnSetter(batch, column, newDefaultValueSetter(column, option), newCalendar(option));

        default:
            return super.newCoalesceColumnSetter(column, option);
        }
    }

    @Override
    public ColumnSetter newColumnSetter(JdbcColumn column, JdbcColumnOption option)
    {
        switch (option.getValueType()) {
        case "byte":
            return new SQLServerByteColumnSetter(batch, column, newDefaultValueSetter(column, option));

        case "time":
            return new SQLServerSqlTimeColumnSetter(batch, column, newDefaultValueSetter(column, option), newCalendar(option));

        default:
            return super.newColumnSetter(column, option);
        }
    }

}
