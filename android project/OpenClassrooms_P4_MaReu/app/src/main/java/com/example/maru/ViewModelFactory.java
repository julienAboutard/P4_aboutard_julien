package com.example.maru;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.data.MeetingRepository;
import com.example.maru.meetinglist.MeetingsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            new MeetingRepository()
                    );
                }
            }
        }

        return factory;
    }

    // This field inherit the singleton property from the ViewModelFactory : it is scoped to the ViewModelFactory
    // Ask your mentor about DI scopes (Singleton, ViewModelScope, ActivityScope)
    @NonNull
    private final MeetingRepository meetingRepository;

    private ViewModelFactory(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingsViewModel.class)) {
            return (T) new MeetingsViewModel(
                    meetingRepository
            );
        }
        /*else if (modelClass.isAssignableFrom(AddNeighbourViewModel.class)) {
            return (T) new AddNeighbourViewModel(
                    meetingRepository
            );
        } else if (modelClass.isAssignableFrom(NeighbourDetailViewModel.class)) {
            return (T) new NeighbourDetailViewModel(
                    MainApplication.getInstance(),
                    meetingRepository
            );
        }*/
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
