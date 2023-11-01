package com.example.maru;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.filter.HourFilterItemStateView;
import com.example.maru.meetinglist.MeetingsViewStateItem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingsViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;

    private final MediatorLiveData<MeetingViewState> meetingViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Map<LocalTime, Boolean>> selectedHoursLiveData = new MutableLiveData<>();

    /**
     * Construct a new ViewModel instance.
     * <p>
     * You should <strong>never</strong> manually construct a ViewModel outside of a
     * {@link ViewModelProvider.Factory}.
     */
    public MeetingsViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void onDeleteMeetingClicked(long neighbourId) {
        meetingRepository.deleteNeighbour(neighbourId);
    }


    public void meetingMediatorLiveData() {
        final LiveData<List<Meeting>> meetingsLiveData = meetingRepository.getMeetingsLiveData();
        setSelectedHoursLiveData(hoursDistribution());

        meetingViewStateMediatorLiveData.addSource(meetingsLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                meetingViewStateMediatorLiveData.setValue(
                    combine(meetings, selectedHoursLiveData.getValue())
                );
            }
        });

        meetingViewStateMediatorLiveData.addSource(selectedHoursLiveData, new Observer<Map<LocalTime, Boolean>>() {
            @Override
            public void onChanged(Map<LocalTime, Boolean> localTimeBooleanMap) {
                meetingViewStateMediatorLiveData.setValue(
                    combine(meetingsLiveData.getValue(), localTimeBooleanMap)
                );
            }
        });
    }

    public void setSelectedHoursLiveData(Map<LocalTime, Boolean> localTimeBooleanMap){
        selectedHoursLiveData.setValue(localTimeBooleanMap);
    }

    public MediatorLiveData<MeetingViewState> getListFilterMeetings(){
        return meetingViewStateMediatorLiveData;
    }

    private MeetingViewState combine(
        @NonNull List<Meeting> meetings,
        @NonNull Map<LocalTime, Boolean> localTimeBooleanMap
    ){
        List<Meeting> filteredMeetings = filterMeetings(meetings, localTimeBooleanMap);
        List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>();
        for (Meeting meeting : filteredMeetings) {
            meetingsViewStateItems.add(
                new MeetingsViewStateItem(
                    meeting.getId(),
                    meeting.getRoom(),
                    meeting.getTopic(),
                    meeting.getTime(),
                    meeting.getMailList()
                )
            );
        }

        List<HourFilterItemStateView>  hourFilterItemStateViews = getHourFilterItemStateView(localTimeBooleanMap);

        return new MeetingViewState(
            meetingsViewStateItems,
            hourFilterItemStateViews
        );
    }
    private Map<LocalTime, Boolean> hoursDistribution() {
        Map<LocalTime, Boolean> hours = new HashMap<>();
        for (int hour = 6; hour <= 22; hour++) {
            hours.put(LocalTime.of(hour, 0), false);
        }
        return hours;
    }
    private List<Meeting> filterMeetings(
        @NonNull List<Meeting> meetings,
        @NonNull Map<LocalTime, Boolean> localTimeBooleanMap
    ) {
        List<Meeting> filteredMeetings = new ArrayList<>();

        if (meetings == null) {return filteredMeetings;}

        for (Meeting meeting : meetings) {
            for (Map.Entry<LocalTime, Boolean> mapHourEntry : localTimeBooleanMap.entrySet()){
                LocalTime hour = mapHourEntry.getKey();
                boolean selectedStatus = mapHourEntry.getValue();

                if (meeting.getTime().equals(hour)) {
                    filteredMeetings.add(meeting);
                }
            }
        }
        return filteredMeetings;
    }

    private List<HourFilterItemStateView> getHourFilterItemStateView(@NonNull Map<LocalTime, Boolean> localTimeBooleanMap) {
        List<HourFilterItemStateView> hourFilterItemStateViews = new ArrayList<>();

        for (Map.Entry<LocalTime, Boolean> hours : localTimeBooleanMap.entrySet()) {
            LocalTime time = hours.getKey();
            boolean selectedStatus = hours.getValue();

            @ColorRes
            int colorText;

            if (selectedStatus) {
                colorText = android.R.color.holo_red_dark;
            } else {
                colorText = android.R.color.white;
            }

            hourFilterItemStateViews.add(
                new HourFilterItemStateView(
                    time,
                    colorText
                )
            );
        }

        return hourFilterItemStateViews;
    }

    public LiveData<List<MeetingsViewStateItem>> getMeetingLiveData() {
        return Transformations.map(meetingRepository.getMeetingsLiveData(), meetings -> {
            List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>();

            for (Meeting meeting : meetings) {
                meetingsViewStateItems.add(
                    new MeetingsViewStateItem(
                        meeting.getId(),
                        meeting.getRoom(),
                        meeting.getTopic(),
                        meeting.getTime(),
                        meeting.getMailList()
                    )
                );
            }
            return meetingsViewStateItems;
        });
    }
}
