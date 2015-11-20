package ldt.com.grocery.services;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;
import ldt.com.grocery.GroceryApplication;
import ldt.com.grocery.database.User;
import ldt.com.grocery.events.GetAllStore;
import ldt.com.grocery.rest.RestClient;
import retrofit.RetrofitError;

public class WebService {
    Context context;
    Handler handler;
    private static ExecutorService executor;
    private static RestClient restClient;
    private final String DBG_PREFIX = "Web Service";
    private static WebService instance;

    public static WebService getInstance(Context context) {
        if (instance == null) {
            instance = new WebService(context);
        }
        return instance;
    }

    public WebService(Context context) {
        this.context = context;
        handler = new Handler();
        executor = Executors.newFixedThreadPool(5);
        restClient = new RestClient(context, GroceryApplication.getConfig("END_POINT"), executor);
    }

    public void request(final Runnable task, final String logText) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                    Log.d(DBG_PREFIX, "process " + logText + " -> DONE");
                } catch (Exception e) {
                    Log.e(DBG_PREFIX, "process " + logText + " -> FAIL ", e);
                }
            }
        });
    }

    public void loadStore() {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<User> data = restClient.loadStore();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetAllStore.Success(data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetAllStore.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetAllStore");
    }
/*

    public void registerFacebook(final String userId, final String snsType, final String userRealName, final String firstName, final String lastName, final String email, final String profileImage, final String gcmRegisterId, final String accessToken) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final RestResponse.RegisterResponse response = restClient.register(
                            userId,
                            snsType,
                            userRealName,
                            firstName,
                            lastName,
                            email,
                            profileImage,
                            gcmRegisterId,
                            accessToken);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new RegisterFacebook.Success(userId, response.userId, response.fullName, response.imageUrl, response.isFirst, response.isActive));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new RegisterFacebook.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "RegisterFacebook");
    }

    public void registerTwitter(final String userId, final String snsType, final String userRealName, final String firstName, final String lastName, final String email, final String profileImage, final String gcmRegisterId, final String accessToken) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final RestResponse.RegisterResponse response = restClient.register(
                            userId,
                            snsType,
                            userRealName,
                            firstName,
                            lastName,
                            email,
                            profileImage,
                            gcmRegisterId,
                            accessToken
                    );
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new RegisterTwitter.Success(userId, response.userId, response.fullName, response.imageUrl, response.isFirst, response.isActive));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new RegisterTwitter.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "RegisterTwitter");
    }

    public void getLocalRec(final String userId, final String lat, final String lng) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Recommendation> data = restClient.getLocalRecommendation(userId, lat, lng);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetLocalRec.Success(data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetLocalRec.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetLocalRec");
    }

    public void getGlobalRec(final String userId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Recommendation> data = restClient.getGlobalRecommendations(userId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetGlobalRec.Success(data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetGlobalRec.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetGlobalRec");
    }

    public void getRecDetails(final String userId, final String recId, final long instanceId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final Recommendation data = restClient.getRecommendationDetails(userId, recId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetRecDetails.Success(data, instanceId));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetRecDetails.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetRecDetails");
    }

    public void getGlobalLeader(final String userId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<User> data = restClient.getGlobalLeadingUsers(userId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetGlobalLeader.Success(data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetGlobalLeader.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetGlobalLeader");
    }

    public void getLocalLeader(final String userId, final String lat, final String lng) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<User> data = restClient.getLocalLeadingUsers(userId, lat, lng);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetLocalLeader.Success(data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetLocalLeader.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetLocalLeader");
    }

    public void getUserProfile(final long instanceId, final String currentUserId, final String userId, final String lat, final String lng) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final User data = restClient.getUserProfile(currentUserId, userId, lat, lng);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetUserProfile.Success(instanceId, data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetUserProfile.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetUserProfile");
    }

    public void uploadProfileImage(final String userId, final String imageEncoded) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final String link = restClient.uploadProfileImage(userId, imageEncoded);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new UploadProfileImage.Success(link));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new UploadProfileImage.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "UploadProfileImage");
    }

    public void giveKudos(final String userId, final String recId, final long instanceId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean wasGiven = restClient.giveKudos(userId, recId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GiveKudos.Success(wasGiven, instanceId));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GiveKudos.Failed(recId, error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GiveKudos");
    }

    public void getKudosGivenList(final String userId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Recommendation> data = restClient.getGivenKudosList(userId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetKudosGiven.Success(data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetKudosGiven.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetKudosGivenList");
    }

    public void getSubmissionList(final String userId, final String userIdToGet) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Recommendation> data = restClient.getSubmissionsList(userId, userIdToGet);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetSubmission.Success(data));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new GetSubmission.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "GetSubmissionList");
    }

    public void addNewRecommendation(final String userId, final String title, final String des, final String link, final double[] loc) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean wasAdded = restClient.addNewRecommendation(
                            userId,
                            title,
                            des,
                            link,
                            loc);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new AddRecommendation.Success(wasAdded));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new AddRecommendation.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "AddNewRecommendation");
    }

    public void logout(final String userId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean wasLoggedOut = restClient.logout(userId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new Logout.Success(wasLoggedOut));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new Logout.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "Logout");
    }

    public void reportRecommendation(final String userId, final String recId, final long instanceId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean isSuccess = restClient.reportRecommendation(userId, recId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new ReportRecommendation.Success(isSuccess, instanceId));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new ReportRecommendation.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "ReportRecommendation");
    }

    public void reportUser(final String userId, final String reportedId, final long instanceId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean isSuccess = restClient.reportUser(userId, reportedId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new ReportUser.Success(isSuccess, instanceId));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new ReportUser.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "ReportUser");
    }

    public void deleteRecommendation(final String userId, final String recId, final long instanceId) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean isSuccess = restClient.deleteRecommendation(userId, recId);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new DeleteRecommendation.Success(isSuccess, instanceId));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new DeleteRecommendation.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "DeleteRecommendation");
    }

    public void updateRecommendation(final String userId, final String recId, final String title, final String des, final String link, final double[] loc) {
        request(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean isSuccess = restClient.updateRecommendation(userId, recId, title, des, link, loc);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new UpdateRecommendation.Success(isSuccess));
                        }
                    });
                } catch (final RetrofitError error) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new UpdateRecommendation.Failed(error.getResponse() != null ? error.getResponse().getStatus() : 500, error.getMessage()));
                        }
                    });
                }
            }
        }, "UpdateRecommendation");
    }
*/

}
