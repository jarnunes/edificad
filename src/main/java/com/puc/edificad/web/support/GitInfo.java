package com.puc.edificad.web.support;

import com.jnunes.spgcore.commons.utils.DateTimeUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GitInfo {


    @Value("${git.branch}")
    String gitBranch;

    @Value("${git.commit.id}")
    String gitCommitId;

    @Value("${git.build.version}")
    String buildVersion;

    @Value("${git.commit.committer.time}")
    String buildDate;


    public String getBuildDate() {
        return DateTimeUtils.formatterISO(buildDate);
    }
}
