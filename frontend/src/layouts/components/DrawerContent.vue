<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: 'funeral-context' }" />
        <VerticalNavLink
            :item="{
                title: '고객',
                to: '/customers',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '장례 정보',
                to: '/funeralInfos',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '부고장',
                to: '/obituaries',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '사망신고서',
                to: '/deathReports',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '장례일정표',
                to: '/schedules',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'funeralpython' }" />
        <VerticalNavLink
            :item="{
                title: 'Python',
                to: '/pythons',
            }"
        />
    </ul>
</template>
