package com.estoque.raphajulia.app.Util;

import java.util.List;

public interface IDAO<E> {

    boolean create(E object);
    E       read(String search_param);
    List<E> readAll();
    boolean update(String search_param, E object);
    boolean delete(String search_param);}
