package com.assi.islam.mytaxi.model;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by islam assi
 *
 * model that carry diff results calculated through {@link DiffUtil} and the new list
 */
public class DiffResultModel<T> {

    private List<T> newList;

    private DiffUtil.DiffResult diffResult;

    public DiffResultModel(List<T> newList, DiffUtil.DiffResult diffResult) {
        this.newList = newList;
        this.diffResult = diffResult;
    }

    public List<T> getNewList() {
        return newList;
    }

    public void setNewList(List<T> newList) {
        this.newList = newList;
    }

    public DiffUtil.DiffResult getDiffResult() {
        return diffResult;
    }

    public void setDiffResult(DiffUtil.DiffResult diffResult) {
        this.diffResult = diffResult;
    }
}
