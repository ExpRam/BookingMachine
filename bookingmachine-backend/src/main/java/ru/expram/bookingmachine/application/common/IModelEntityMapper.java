package ru.expram.bookingmachine.application.common;

public interface IModelEntityMapper<T, U> {

    T mapToModel(U entity);
    U mapToEntity(T model);
}
